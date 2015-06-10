/****************************************
 * Adam Tracy                           *
 * Countries of the World Assignment 3  *
 * UserApp                              *
 * Read from the transaction file       *
 * and process user transactions        *
 ***************************************/
package cotw3;

import java.io.*;

public class UserApp {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

		// declare some variables
		String transDataSuffix = "1";
		int RRN;
		String tranCode = null;
		String otherTran = null;
		int n = 0;

		// objects as well
		UI ui = new UI(transDataSuffix);
		DataTable dt = new DataTable();
		dt.writeStatus("STATUS > UserApp Started", n);
		dt.writeStatus("STATUS > TransDataFile Opened. TransData "
				+ transDataSuffix);

		// ********************************************************************************
		// loop till done. process the transdata information and switch as
		// necessary
		n = 0;
		//process
		tranCode = ui.processTrans();
		otherTran = ui.getRestOfLine();
		while (!ui.isDone()) {
			if (tranCode != null) {
				switch (tranCode) {
				//select by code
				case "SC":
					ui.writeToLog(tranCode, otherTran);
					RRN = dt.hashFunction(otherTran.toCharArray());
					boolean found = dt.searchHash(RRN, otherTran);
					if(!found){
						ui.writeToLog("Country data not found.");
					}
					n++;
					break;
				//delete by code
				case "DC":
					ui.writeToLog(tranCode, otherTran);
					ui.writeToLog("[SORRY! Delete by code module not yet working.]");
					n++;
					break;

				default:// if nothing else, make the user feel bad.
					ui.writeToLog("Invalid Code.%n");
					n++;
					break;

				}
			}
			//process next one.
			tranCode = ui.processTrans();
			otherTran = ui.getRestOfLine();
		}

		// wrap things up
		dt.writeStatus("STATUS > UserApp Finished", n);
		ui.finishUp();
		dt.writeStatus("STATUS > TransDataFile closed");

	}
}
