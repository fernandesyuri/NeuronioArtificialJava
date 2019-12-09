
import java.util.Arrays;

/* A logistic regression algorithm for binary classification implemented using Newton's method and 
 * a Wolfe condition based inexact line-search.
 *created by Alrecenk for inductivebias.com May 2014
 */
public class Newton {

    double w[]; //the weights for the logistic regression
    int degree; // degree of polynomial used for preprocessing
    int numeroEpocas;
    FuncaoAtivacao fncAtivacao;

    //preprocessed list of input/output used for calculating error and its gradients
    double input[][];
    double output[];

    //these evaluation counters increment on every call to error, gradient, and hessian respectively
    public int feval, geval, heval;

    //create a logistic regression for binary classification on the given inputand output
    //with polynomial expansion of the given degree
    public Newton(double in[][], boolean out[], int degree, int numeroEpocas, FuncaoAtivacao fncAtivacao) {
        this.degree = degree;
        this.numeroEpocas = numeroEpocas;
        this.fncAtivacao = fncAtivacao;

        input = new double[in.length][];
        output = new double[out.length];

        for (int k = 0; k < in.length; k++) {
            input[k] = polynomial(in[k], degree);
        }

        int postotal = 0, negtotal = 0;
        double pos[] = new double[input[0].length];
        double neg[] = new double[input[0].length];
        //get totals for negative and positive points
        for (int k = 0; k < in.length; k++) {
            if (out[k]) {
                output[k] = 1;
                pos = add(pos, input[k]);
                postotal++;
            } else {
                neg = add(neg, input[k]);
                negtotal++;
            }
        }

        //for non-polynomial case use starting weights pointing from centroid of negatives to centroid of positives.
        if (postotal >= 1 && negtotal >= 1 && degree == 1) {
            double pp = 0, pn = 0, nn = 0;
            for (int k = 0; k < pos.length; k++) {
                pos[k] /= postotal;//scale totals to get center of each class
                neg[k] /= negtotal;
                pp += pos[k] * pos[k];
                pn += pos[k] * neg[k];// calculate relevant dot products
                nn += neg[k] * neg[k];
            }
            //assuming w = alpha * (poscenter- negcenter) with b in the last slot
            //solve for alpha and b so that poscenter returns 0.75 and negcenter returns 0.25
            double alphab[] = lineIntersection(pp - pn, 1, fncAtivacao.inversa(0.75), pn - nn, 1, fncAtivacao.inversa(0.25));
            w = new double[input[0].length];
            for (int k = 0; k < w.length - 1; k++) {
                w[k] = alphab[0] * (pos[k] - neg[k]); // alpha * pos-neg
            }
            w[w.length - 1] = alphab[1]; // bias is on the end of w
        } else {
            w = new double[pos.length];
        }

        //run newton's method to get locally optimal weights
        w = newtonMethod(w, 0.00001 * input.length, numeroEpocas);
        System.out.println("Pesos: " + Arrays.toString(w));
        //dump data after the curve fitting is complete
        input = null;
        output = null;
    }

    //applies the logistic regression to predict a new point's probability of being in the positive class
    public double apply(double i[]) {
        return fncAtivacao.aplicar(dot(w, polynomial(i, degree)));
    }

    //returns the error of a logistic regressions with weights w on the given input and output
    //output should be in the form 0 for negative, 1 for positive
    public double error(double w[]) {
        feval++;//keep track of how many times this has been called
        double error = 0;
        for (int k = 0; k < input.length; k++) {
            double diff = fncAtivacao.aplicar(dot(w, input[k])) - output[k];
            error += diff * diff;
        }
        return error;
    }

    //returns the gradient of error with respect to weights 
    //for a logistic regression with weights w on the given input and output
    //output should be in the form 0 for negative, 1 for positive
    public double[] gradient(double w[]) {
        geval++;//keep track of how many times this has been called
        double g[] = new double[w.length];
        for (int k = 0; k < input.length; k++) {
            double dot = dot(w, input[k]);
            double coef = 2 * (fncAtivacao.aplicar(dot) - output[k]) * fncAtivacao.derivada(dot);
            for (int j = 0; j < g.length; j++) {
                g[j] += input[k][j] * coef;
            }
        }
        return g;
    }

    //returns the hessian (gradient of gradient) of error with respect to weights 
    //for a logistic regression with weights w on the given input and output
    //output should be in the form 0 for negative, 1 for positive
    public double[][] hessian(double w[]) {
        heval++;//keep track of how many times this has been called
        double h[][] = new double[w.length][];
        //second derivative matrices are always symmetric so we only need triangular portion
        for (int j = 0; j < h.length; j++) {
            h[j] = new double[j + 1];
        }
        for (int k = 0; k < input.length; k++) {
            //calculate coefficient{
            double dot = dot(w, input[k]);
            double ds = fncAtivacao.derivada(dot);
            double coef = 2 * (ds * ds + fncAtivacao.derivadaSegunda(dot) * (fncAtivacao.aplicar(dot) - output[k]));
            for (int j = 0; j < h.length; j++) {// add x * x^t * coef to hessian
                for (int l = 0; l <= j; l++) {
                    h[j][l] += input[k][j] * input[k][l] * coef;
                }
            }
        }
        return h;
    }

    //starting from w0 searches for a weight vector using Newton's method
    //and Wolfe condition line-search until the gradient magnitude is below tolerance
    //or a maximum number of iterations is reached.
    public double[] newtonMethod(double w0[], double tolerance, int maxiter) {
        double wLocal[] = w0;
        double gradient[] = gradient(w0);
        int iteration = 0;
        while (Math.sqrt(dot(gradient, gradient)) > tolerance && iteration < maxiter) {
            iteration++;
            //get the second derivative matrix
            double hessian[][] = hessian(wLocal);

            //perform an LDL decomposition and substitution to solve the system of equations Hd = -g  for the Newton step
            //(See Linear Least Squares Article on Inductive Bias for details on this technique)
            //calculate the LDL decomposition in place with D over top of the diagonal
            for (int j = 0; j < wLocal.length; j++) {
                for (int k = 0; k < j; k++) {//D starts as Hjj then subtracts
                    hessian[j][j] -= hessian[j][k] * hessian[j][k] * hessian[k][k];//Ljk^2 * Dk
                }
                for (int i = j + 1; i < wLocal.length; i++) {//L over the lower diagonal
                    for (int k = 0; k < j; k++) {//Lij starts as Hij then subtracts
                        hessian[i][j] -= hessian[i][k] * hessian[j][k] * hessian[k][k];//Ljk^2*D[k]
                    }
                    hessian[i][j] /= hessian[j][j];//divide Lij by Dj
                }
            }

            //check if D elements are all positive to make sure Hessian was positive definite and Newton step goes to a minimum
            boolean positivedefinite = true;
            for (int k = 0; k < wLocal.length && positivedefinite; k++) {
                positivedefinite &= hessian[k][k] > 0;
            }

            //right hand side for Newton's method is negative gradient
            double[] newton = scale(gradient, -1);
            if (positivedefinite) { // if H was pd then get newton direction, otherwise leave it as -gradient
                //in-place forward substitution with L
                for (int j = 0; j < wLocal.length; j++) {
                    for (int i = 0; i < j; i++) {
                        newton[j] -= hessian[j][i] * newton[i];
                    }
                }
                //Multiply by inverse of D matrix
                for (int k = 0; k < wLocal.length; k++) {//inverse of diagonal
                    newton[k] /= hessian[k][k];//is 1 / each element
                }
                // in-place backward substitution with L^T
                for (int j = wLocal.length - 1; j >= 0; j--) {
                    for (int i = j + 1; i < wLocal.length; i++) {
                        newton[j] -= hessian[i][j] * newton[i];
                    }
                }
            }

            //calculate step-size    
            double alpha = stepSize(this, wLocal, newton, 1, 500, 0.001, 0.9);// then use it
            //apply step
            wLocal = add(wLocal, scale(newton, alpha));
            //calculate gradient for exit condition and next step 
            gradient = gradient(wLocal);
        }

        return wLocal;
    }

    //Performs a binary search to satisfy the Wolfe conditions
    //returns alpha where next x =should be x0 + alpha*d 
    //guarantees convergence as long as search direction is bounded away from being orthogonal with gradient
    //x0 is starting point, d is search direction, alpha is starting step size, maxit is max iterations
    //c1 and c2 are the constants of the Wolfe conditions (0.1 and 0.9 can work)
    public static double stepSize(Newton problem, double[] x0, double[] d, double alpha, int maxit, double c1, double c2) {

        //get error and gradient at starting point  
        double fx0 = problem.error(x0);
        double gx0 = dot(problem.gradient(x0), d);

        //bound the solution
        double alphaL = 0;
        double alphaR = 10000;

        for (int iter = 1; iter <= maxit; iter++) {
            double[] xp = add(x0, scale(d, alpha)); // get the point at this alpha
            double erroralpha = problem.error(xp); //get the error at that point
            if (erroralpha >= fx0 + alpha * c1 * gx0) { // if error is not sufficiently reduced
                alphaR = alpha;//move halfway between current alpha and lower alpha
                alpha = (alphaL + alphaR) / 2.0;
            } else {//if error is sufficiently decreased 
                double slopealpha = dot(problem.gradient(xp), d); // then get slope along search direction
                if (slopealpha <= c2 * Math.abs(gx0)) { // if slope sufficiently closer to 0
                    return alpha;//then this is an acceptable point
                } else if (slopealpha >= c2 * gx0) { // if slope is too steep and positive then go to the left
                    alphaR = alpha;//move halfway between current alpha and lower alpha
                    alpha = (alphaL + alphaR) / 2;
                } else {//if slope is too steep and negative then go to the right of this alpha
                    alphaL = alpha;//move halfway between current alpha and upper alpha
                    alpha = (alphaL + alphaR) / 2;
                }
            }
        }

        //if ran out of iterations then return the best thing we got
        return alpha;
    }

    //dot product
    public static double dot(double[] a, double[] b) {
        double c = 0;
        for (int k = 0; k < a.length; k++) {
            c += a[k] * b[k];
        }
        return c;
    }

    //returns a vector = to a*s
    public static double[] scale(double[] a, double s) {
        double[] b = new double[a.length];
        for (int k = 0; k < a.length; k++) {
            b[k] = a[k] * s;
        }
        return b;
    }

    //returns the sum of two vectors
    public static double[] add(double[] a, double[] b) {
        double[] c = new double[a.length];
        for (int k = 0; k < a.length; k++) {
            c[k] = a[k] + b[k];
        }
        return c;
    }

    //returns the sum of two vectors
    public static double[] subtract(double[] a, double[] b) {
        double[] c = new double[a.length];
        for (int k = 0; k < a.length; k++) {
            c[k] = a[k] - b[k];
        }
        return c;
    }

    //Creates a new input vector which is a 1, and each input raised to integer powers up to degree
    //when called with degree=1 this simply adds a bias value to the input vector
    //otherwise it creates a separable polynomial of the given degree
    public static double[] polynomial(double[] input, int degree) {
        double[] output = new double[1 + input.length * degree];
        int i = 0, k, j;
        for (k = 0; k < input.length; k++) {//each input
            for (j = 1; j <= degree; j++) {// raised to each power
                output[i] = (double) Math.pow(input[k], j);
                i++;
            }
        }
        output[i] = 1; //constant
        return output;
    }

    //returns the intersection of 2D lines given in standard form
    // a1*x + b1*y = c1  and a2*x + b2*y = c2
    public static double[] lineIntersection(double a1, double b1, double c1, double a2, double b2, double c2) {
        double det = a1 * b2 - a2 * b1;
        if (det == 0) {
            return null;
        } else {
            return new double[]{(c1 * b2 - b1 * c2) / det, (a1 * c2 - c1 * a2) / det};
        }
    }

    public double[] getPesos() {
        return this.w;
    }
}
