package cn.iliker.mall.entity.stateattr;
//发货状态
public enum PostMethod {
	NORMAL {
		public String getName() {
			return "普通快递";
		}
	},
	POINT{
		public String getName() {
			return "门店自提";
		}
	};
	public abstract String getName();
}
