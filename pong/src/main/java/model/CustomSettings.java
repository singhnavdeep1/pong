package model;

import java.io.*;

public class CustomSettings implements Serializable {
	
	private final static long serialVersionUID = 62142069L;
	
	private final static String SYS_SEP = System.getProperty("file.separator");
	
	public final double width;
	public final double height;
	
	public final RacketController playerA;
	public final RacketController playerB;
	
	public final int ballAmount;
	
	public final double[] initialRacketSizes;
	public final double[] initialRacketSpeeds;
	
	public final Double countdown;
	public final Integer maxScore;
	
	public final double[] coefRacketSizes;
	public final double[] coefRacketAccel;
	public final double[] coefBallAccel;
	
	public CustomSettings(double width, double height, 
			RacketController playerA, RacketController playerB, int ballAmnt,
			double[] iSizes, double[] iSpeeds, Double cd, Integer maxS,
			double[] cSizes, double[] cCoefs, double[] aCoefs) {
		if (aCoefs.length != ballAmnt) throw new IllegalArgumentException("Each ball needs an acceleration coefficient");
		
		this.width = width;
		this.height = height;
		
		this.playerA = playerA;
		this.playerB = playerB;
		
		this.ballAmount = ballAmnt;
		
		this.initialRacketSizes = iSizes.clone();
		this.initialRacketSpeeds = iSpeeds.clone();
		
		this.countdown = cd;
		this.maxScore = maxS;
		
		this.coefRacketSizes = cSizes.clone();
		this.coefRacketAccel = cCoefs.clone();
		this.coefBallAccel = aCoefs.clone();
	}
	
	public void write(String path) throws IOException {	//Attrapez l'exception dans l'interface graphique pour faire un message d'erreur
		File destination = new File(path);
		if (!destination.exists()) destination.createNewFile();	//S'il y a une IOException, une erreur s'est produite lors de la communication
		FileOutputStream output = new FileOutputStream(destination);
		ObjectOutputStream objectStream = new ObjectOutputStream(output);
		objectStream.writeObject(this);
		objectStream.close();
	}
	
	public static CustomSettings read(String path) throws IOException, ClassNotFoundException {	//Pareil ici
		FileInputStream fileInput = new FileInputStream(path);
		ObjectInputStream inputStream = new ObjectInputStream(fileInput);
		CustomSettings res = (CustomSettings) inputStream.readObject();	//Cette instruction peut lever plusieurs exception, voir la documentation java
		inputStream.close();
		return res;
	}
	
	private static String arrStr(double[] arr) {
		String res = "{";
		for (int i = 0 ; i < arr.length ; i++) {
			res += arr[i];
			if (i < arr.length - 1) res += ", ";
		}
		return res + "}";
	}
	
	public String toString() {
		String res = "Dimensions : " + width + "x" + height + "\n";
		res += "Joueur A : " + playerA + " ; Joueur B : " + playerB + "\n";
		res += "Tailles initiales : " + arrStr(initialRacketSizes) + "\n";
		res += "Vitesses initiales : " + arrStr(initialRacketSpeeds) + "\n";
		res += "Coefficiencts de taille : " + arrStr(coefRacketSizes) + "\n";
		res += "Coefficients d'acceleration : " + arrStr(coefRacketAccel) + "\n";
		res += "Compte à rebours : " + countdown + "\n";
		res += "Score maximal : " + maxScore + "\n";
		res += "Nombre de balles : " + ballAmount + "\n";
		res += "Coefficients d'acceleration des balles : " + arrStr(coefBallAccel) + "\n";
		return res;
	}
	
	public static void main(String[] args) {
		double[] iSizes = {100, 100};
        double[] iSpeeds = {300, 300};
        double[] cSizes = {1, 1};
        double[] cCoefs = {1, 1};
        double[] aCoefs = {1, 1};
        CustomSettings cs = new CustomSettings(600, 300, null, null, 2, iSizes, iSpeeds, null, null, cSizes, cCoefs, aCoefs);
        CustomSettings cs2 = null;
        try {
        	cs.write("test.ser");
        	System.out.println("Wrote class successfully");
        } catch (IOException ioe) {
        	ioe.printStackTrace();
        }
        try {
        	cs2 = CustomSettings.read("test.ser");
        	System.out.println("Read file successfully");
        } catch (IOException ioe) {
        	ioe.printStackTrace();
        } catch (ClassNotFoundException cnfe) {
        	cnfe.printStackTrace();
        }
        System.out.println(cs);
        System.out.println(cs2);
	}
	
}
