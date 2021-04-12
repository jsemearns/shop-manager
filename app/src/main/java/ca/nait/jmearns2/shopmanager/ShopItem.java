package ca.nait.jmearns2.shopmanager;

public class ShopItem
{
    int shopItemId;
    String name;
    float price;
    byte[] image;

    public ShopItem(int shopItemId, String name, float price, byte[] image)
    {
        this.shopItemId = shopItemId;
        this.name = name;
        this.price = price;
        this.image = image;
    }

    public int getShopItemId()
    {
        return shopItemId;
    }

    public void setShopItemId(int shopItemId)
    {
        this.shopItemId = shopItemId;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public float getPrice()
    {
        return price;
    }

    public void setPrice(float price)
    {
        this.price = price;
    }

    public byte[] getImage()
    {
        return image;
    }

    public void setImage(byte[] image)
    {
        this.image = image;
    }
}
