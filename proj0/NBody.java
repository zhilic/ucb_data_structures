import java.util.ArrayList;

public class NBody {

	/** Given a file name, readRadius should return a double corresponding to 
	 *  the radius of the the universe in that file.
	 *  The test method is static, so readRadius has to be static.
	 */ 
	public static double readRadius(String fname) {
		In in = new In(fname);
		int planetNo = in.readInt();
		double r = in.readDouble();
		return r;	
	}

	public static Planet[] readPlanets(String fname) {
		ArrayList<Planet> planets = new ArrayList<>();

		In in = new In(fname);
		int planetNo = in.readInt();
		double r = in.readDouble();

		while (!in.isEmpty()) {
			// The last line in txt file is a comment, not following the format of planet data
			double xP;  // to solve scope issue
			try {
				xP = in.readDouble();
			} catch(java.util.InputMismatchException e) {
				break;
			}
			double yP = in.readDouble();
			double xV = in.readDouble();
			double yV = in.readDouble();
			double mass = in.readDouble();
			String img = in.readString();
			planets.add(new Planet(xP, yP, xV, yV, mass, img));
		}

		Planet[] res = planets.toArray(new Planet[planets.size()]);
		return res;
	}

	public static void main(String[] args) {
		// Collect all needed input
		double T = Double.parseDouble(args[0]);
		double dt = Double.parseDouble(args[1]);
		String filename = args[2];
		double R = readRadius(filename);
		Planet[] planets = readPlanets(filename);

		// Drawing the background
		StdDraw.setScale(-R, R);
		StdDraw.clear();
		StdDraw.picture(0, 0, "./images/starfield.jpg");
		StdDraw.show();

		// Drawing each planet
		for (Planet p : planets) {
			p.draw();
		}

		// Enables double buffering, an animation technique
		StdDraw.enableDoubleBuffering();

		double tt = 0.0;
		while(tt <= T) {
			double[] xForces = new double[planets.length];
			double[] yForces = new double[planets.length];
			for(int i = 0; i < planets.length; i++) {
				xForces[i] = planets[i].calcNetForceExertedByX(planets);
				yForces[i] = planets[i].calcNetForceExertedByY(planets);
			}
			for(int i = 0; i < planets.length; i++) {
				planets[i].update(dt, xForces[i], yForces[i]);
			}
			StdDraw.clear();
			StdDraw.picture(0, 0, "./images/starfield.jpg");
			for (Planet p : planets) {
				p.draw();
			}
			StdDraw.show();
			StdDraw.pause(10);

			tt += dt;
		}

		StdOut.printf("%d\n", planets.length);
		StdOut.printf("%.2e\n", R);
		for (int i = 0; i < planets.length; i++) {
		    StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
		                  planets[i].xxPos, planets[i].yyPos, planets[i].xxVel,
		                  planets[i].yyVel, planets[i].mass, planets[i].imgFileName);   
		}
	}

}