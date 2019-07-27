package cn.iliker.mall.entity.stateattr;
//发货状态
public enum PayMethod {
	ONLING {
		public String getName() {
			return "在线支付";
		}
	},
	OFFLING{
		public String getName() {
			return "货到付款";
		}
	};
	public abstract String getName();
}
