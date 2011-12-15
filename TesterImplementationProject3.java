package ics311;

public class TesterImplementationProject3 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Usage: java TesterImplementationProject3 filename threshold");
            System.out.println("Where filename is the data file to be read, and threshold is a value between 0 and 1 for the community granularity");
            System.exit(1);
        }
		Graph g = new Graph();
		g.readFromFile(args[0]);
		g.calcMinsMaxAvg();
		g.finalPrint();
		System.out.println();
		g.buildDendrogram(Double.parseDouble(args[1]));
	}
}