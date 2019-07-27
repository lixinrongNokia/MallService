package cn.iliker.mall.service.impl;

class QueryCallback /*implements CompassCallback<List<Goods>>*/ {
    /*private final String keyword;
    private final int firstResult;
    private final int maxResult;

    public QueryCallback(String keyword, int firstResult, int maxResult) {
        this.firstResult = firstResult;
        this.maxResult = maxResult;
        this.keyword = keyword;
    }

    @Override
    public List<Goods> doInCompass(CompassSession compassSession) throws CompassException {
        CompassHits compassHit = compassSession.find(keyword);//5
        List<Goods> list = new ArrayList<>();
        int length = firstResult + maxResult;
        if(length>compassHit.length()) length = compassHit.length();
        for(int i = firstResult ; i < length ; i++){
            Goods goods = (Goods) compassHit.data(i);
            if(compassHit.highlighter(i).fragment("goodName")!=null)
                goods.setGoodName(compassHit.highlighter(i).fragment("goodName"));
            if(compassHit.highlighter(i).fragment("goodsDesc")!=null)
                goods.setGoodsDesc(compassHit.highlighter(i).fragment("goodsDesc"));
            list.add(goods);
        }
        return list;
    }*/
}
