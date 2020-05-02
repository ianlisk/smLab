package simModel;

import simulationModelling.SequelActivity;

class Move extends SequelActivity {
	static SMLabTesting model;

	private static final double DURATION = 1.0 / 60;
	private static final int LOOP_SIZE = 48;

	@Override
	protected double duration() {
		return DURATION;
	}

	@Override
	public void startingEvent() {
		// NONE
	}

	@Override
	protected void terminatingEvent() {
		model.output.numMoves++;
		model.output.countOccupyingOfBuffer();
		// 每次步进，基准位置自增1.（传送带数组不变）
		model.rqTransportationLoop.loadAreaPosition = (model.rqTransportationLoop.loadAreaPosition + 1) % LOOP_SIZE;
		udpMoveToFromLoop();
		Move seqAct = new Move();
		seqAct.startingEvent();
		model.scheduleActivity(seqAct);
	}

	/* UDPs */
	protected void udpMoveToFromLoop() {
		int LU_in = model.rqTransportationLoop.loadAreaPosition;// 基准位置：LU_inputbuffer在传送带上的位置
		int LU_out = (LU_in + Constants.BUFFER_SIZE) % LOOP_SIZE;
		// System.out.printf("LU_in=%d , LU_out= %d \n", LU_in, LU_out);
		for (int cid : Constants.EXTENDED_CID_ARRAY) {// 遍历每个cell
			// System.out.println("********************************udpMoveToFromLoop
			// 处理前的 qInputBuffer[" + cid
			// + "].list = " + model.qInputBuffer[cid].list);
			int offset = (cid + 1) % Constants.EXTENDED_CID_ARRAY.length;
			int cid_in = (LU_in + offset * Constants.CELL_DISTANCE) % LOOP_SIZE;
			int cid_ou = (LU_out + offset * Constants.CELL_DISTANCE) % LOOP_SIZE;
			int shid_in = model.rqTransportationLoop.position[cid_in];// 传送带cid_in位置上的值，即之前初始化时存的shId
																		// TODO
			// System.out.println(
			// "********************************udpMoveToFromLoop
			// model.rqTransportationLoop.position传送带数组 = "
			// + Arrays.toString(model.rqTransportationLoop.position));
			// System.out.printf("cid_in=%d , cid_ou= %d , sid_in= %d \n",
			// cid_in, cid_ou, shid_in);
			// If the position(cid_in) has a sample holder
			if (shid_in >= Constants.EMPTY_SAMPLE_HOLDER) {
				if (cid == Constants.LU) {
					boolean new_LU_logic = model.logicConfiguration == LoadUnloadDevice.logicType.NEW_LOGIC;
					boolean hasSpace = model.qInputBuffer[cid].isAvailable();
					boolean isSampleHolderEmpty = null == model.rSampleHolder[shid_in].sample;
					int sampleStep = isSampleHolderEmpty ? 0 : model.rSampleHolder[shid_in].sample.step;
					boolean isTestFinish = !isSampleHolderEmpty
							&& model.rSampleHolder[shid_in].sample.sequence[sampleStep] == Constants.LU;

					if (new_LU_logic) {
						if ((isTestFinish && hasSpace) || (isSampleHolderEmpty && model.qInputBuffer[cid].list
								.size() < Constants.LU_BUFFER_SUGGESTED_WAITING_NUMBER)) {
							model.qInputBuffer[cid].insert(shid_in);
							model.rqTransportationLoop.position[cid_in] = Constants.EMPTY_SAMPLE_HOLDER - 1;
						}
					} else {
						if (hasSpace) {
							model.qInputBuffer[cid].insert(shid_in);
							model.rqTransportationLoop.position[cid_in] = Constants.EMPTY_SAMPLE_HOLDER - 1;
						}
					}
				} else {
					Sample icSample = model.rSampleHolder[shid_in].sample;
					if (icSample != null && icSample.sequence[icSample.step] == cid
							&& model.qInputBuffer[cid].isAvailable()) {
						model.qInputBuffer[cid].insert(shid_in);
						model.rqTransportationLoop.position[cid_in] = Constants.EMPTY_SAMPLE_HOLDER - 1;
					}
				}
			}
			// If the current outputBuffer(cid) has something and the convoyer
			// position is empty
			if (model.qOutputBuffer[cid].list.size() > 0
					&& model.rqTransportationLoop.position[cid_ou] == Constants.EMPTY_SAMPLE_HOLDER - 1) {
				int sid_ou = model.qOutputBuffer[cid].remove();
				model.rqTransportationLoop.position[cid_ou] = sid_ou;
			}
		}
		// for (int cid : Constants.EXTENDED_CID_ARRAY) {
		// System.out.println("********************************udpMoveToFromLoop处理后的qInputBuffer["
		// + cid + "].list = "
		// + model.qInputBuffer[cid].list);
		// }

	}

}