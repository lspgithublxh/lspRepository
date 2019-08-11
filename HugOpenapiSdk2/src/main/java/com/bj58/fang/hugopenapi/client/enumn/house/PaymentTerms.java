package com.bj58.fang.hugopenapi.client.enumn.house;

public enum PaymentTerms {
	yayifuyi(1), yayifuer(2), fueryayi(3), fueryaer(4), fusanyayi(5), fusanyaer(6), mianyi(7), bannianfu(8), nianfu(
			9), bannianfuyayi(10), bannianfubuya(11), nianfubuya(12), nianfuyayi(13);
	private int value;

	private PaymentTerms(int value) {
		this.value = value;
	}
	public int getValue() {
		return value;
	}
}