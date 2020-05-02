package simModel;

public class LogPrinter {

	static SMLabTesting model;

	protected static void print() {
		String[] testers = new String[Constants.TESTER_SIZE];
		for (int cid : Constants.DEFAULT_CID_ARRAY) {
			testers[cid] = String.format("[%s][%s][%s][%s][%s]", getTesterStatus(cid, 0), getTesterStatus(cid, 1),
					getTesterStatus(cid, 2), getTesterStatus(cid, 3), getTesterStatus(cid, 4));
		}
		String[] ioBuffer = new String[6];
		for (int cid : Constants.EXTENDED_CID_ARRAY) {
			ioBuffer[cid] = String.format("[%d][%d]", model.qInputBuffer[cid].list.size(),
					model.qOutputBuffer[cid].list.size());
		}
		System.out.printf("Number of arrived samplers [%d]\n", model.qNewSampleBuffer.list.size());
		System.out.println("LU " + String.format("[%s]", (model.rLoadUnloadDevice.busy) ? "B" : "I") + " "
				+ ioBuffer[Constants.LU]);
		System.out.println("C1 " + testers[Constants.C1] + " " + ioBuffer[Constants.C1]);
		System.out.println("C2 " + testers[Constants.C2] + " " + ioBuffer[Constants.C2]);
		System.out.println("C3 " + testers[Constants.C3] + " " + ioBuffer[Constants.C3]);
		System.out.println("C4 " + testers[Constants.C4] + " " + ioBuffer[Constants.C4]);
		System.out.println("C5 " + testers[Constants.C5] + " " + ioBuffer[Constants.C5]);
		System.out.println("Repair/Clean");
		System.out.printf("%4s", " ");
		for (int i = 0; i < 5; i++)
			System.out.printf("%-10d", i);
		System.out.println();
		for (int cid : Constants.DEFAULT_CID_ARRAY) {
			System.out.printf("C%d  ", cid + 1);
			for (Tester tester : model.rcTester[cid]) {
				if (cid == Constants.C2) {
					System.out.printf("%-10d", Constants.TESTS_BEFORE_CLEAN - tester.numTests);
				} else {
					System.out.printf("%-10.2f", tester.timeToFail);
				}
			}
			System.out.println("");
		}
	}

	private static String getTesterStatus(int cid, int tid) {
		if (tid < model.rcTester[cid].length) {
			if (Tester.Status.IDLE == model.rcTester[cid][tid].status)
				return "I";
			if (Tester.Status.BUSY == model.rcTester[cid][tid].status)
				return "B";
			if (Tester.Status.DOWN == model.rcTester[cid][tid].status)
				return "D";
			else
				return null;
		}
		return " ";
	}

}
