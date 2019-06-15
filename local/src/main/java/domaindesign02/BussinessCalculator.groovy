package domaindesign02

class BussinessCalculator {

    ProrityTotal calculate(String productId){
        int total = 0;
        List<BacklogItem> items = new BacklogItemRepo().getItems(productId)
        items.each {
            v -> total++
        }
        return new ProrityTotal()
    }

}
