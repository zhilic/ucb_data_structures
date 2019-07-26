import java.lang.*;

public class Planet {

	public double xxPos;  // current x position, m
	public double yyPos;  // current y position, m
	public double xxVel;  // current velocity in the x direction, m
	public double yyVel;  // current velocity in the y direction, m
	public double mass;   // mass, kg
	public String imgFileName;
	static final double G = 6.67e-11;   // the gravity constance, N*m^2/kg^2

	public Planet(double xP, double yP, double xV, double yV, double m, String img) {
		xxPos = xP;
		yyPos = yP;
		xxVel = xV;
		yyVel = yV;
		mass = m;
		imgFileName = img;
	}

	/**
	 * The second constructor takes in a Planet object and 
	 * initialize an identical Planet object (i.e. a copy)
	 */
	public Planet(Planet p) {
		xxPos = p.xxPos;
		yyPos = p.yyPos;
		xxVel = p.xxVel;
		yyVel = p.yyVel;
		mass = p.mass;
		imgFileName = p.imgFileName;		
	}

	/** calcDistance method takes in a single Planet and return the distance 
	 *  between the supplied planet and the planet that is doing the calculation.
	 */
	public double calcDistance(Planet p) {
		return Math.sqrt(Math.pow(this.xxPos - p.xxPos, 2)
					   + Math.pow(this.yyPos - p.yyPos, 2));
	}

	/** calcForceExertedBy method takes in a planet, and returns a double 
	 *  describing the force exerted on this planet by the given planet
	 */
	public double calcForceExertedBy(Planet p) {
		return G * this.mass * p.mass / Math.pow(this.calcDistance(p),2);
	}

	public double calcForceExertedByX(Planet p) {
		return (p.xxPos - this.xxPos) / this.calcDistance(p)
				* this.calcForceExertedBy(p);
	}

	public double calcForceExertedByY(Planet p) {
		return (p.yyPos - this.yyPos) / this.calcDistance(p)
				* this.calcForceExertedBy(p);
	}

	/** methods calcNetForceExertedByX and calcNetForceExertedByY each takes in 
	 *  an array of Planets and calculate the net X and net Y force exerted by 
	 *  all planets in that array upon the current Planet.
	 */
	public double calcNetForceExertedByX(Planet[] allps) {
		double Fx = 0.0;
		// for (int i = 0; i < allps.length; i++) {
		// 	if (this != allps[i]) {
		// 		Fx += this.calcForceExertedByX(allps[i]);
		// 	}
		// }

		// Use enhanced For-loops
		for (Planet p : allps) {
			if (this != p) {
				Fx += this.calcForceExertedByX(p);
			}
		}
		return Fx;
	}

	public double calcNetForceExertedByY(Planet[] allps) {
		double Fy = 0.0;
		// for (int i = 0; i < allps.length; i++) {
		// 	if (this != allps[i]) {
		// 		Fy += this.calcForceExertedByY(allps[i]);
		// 	}
		// }

		// Use enhanced For-loops
		for (Planet p : allps) {
			if (this != p) {
				Fy += this.calcForceExertedByY(p);
			}
		}
		return Fy;
	}


	/** Update this planet's velocity and position if an x-force fX (Newton) and
	 *  a y-force fY (Newton) were applied for dt seconds.
	 */
	public void update(double dt, double fX, double fY) {
		double aX = fX / this.mass;
		double aY = fY / this.mass;
		this.xxVel += aX * dt;
		this.yyVel += aY * dt;
		this.xxPos += this.xxVel * dt;
		this.yyPos += this.yyVel * dt;
	}

	public void draw() {
		StdDraw.picture(this.xxPos, this.yyPos, "./images/"+this.imgFileName);
	}

}