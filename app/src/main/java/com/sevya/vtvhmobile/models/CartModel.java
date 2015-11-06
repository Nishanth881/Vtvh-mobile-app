package com.sevya.vtvhmobile.models;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

import java.math.BigDecimal;
import java.util.Hashtable;

/**
 * Created by abhinaym on 5/11/15.
 */
public class CartModel implements KvmSerializable {

    private Integer Actid;
    private Integer SalesmanId;
    private Integer ModalId;
    private Float SalePrice;
    private Float TotalPrice;
    private Integer SpId;
    private Integer Qty;
    private Integer CartId;
    private Integer CartModelId;

    public Integer getActid() {
        return Actid;
    }

    public void setActid(Integer actid) {
        Actid = actid;
    }

    public Integer getSalesmanId() {
        return SalesmanId;
    }

    public void setSalesmanId(Integer salesmanId) {
        SalesmanId = salesmanId;
    }

    public Integer getModalId() {
        return ModalId;
    }

    public void setModalId(Integer modalId) {
        ModalId = modalId;
    }

    public Float getSalePrice() {
        return SalePrice;
    }

    public void setSalePrice(Float salePrice) {
        SalePrice = salePrice;
    }

    public Float getTotalPrice() {
        return TotalPrice;
    }

    public void setTotalPrice(Float totalPrice) {
        TotalPrice = totalPrice;
    }

    public Integer getSpId() {
        return SpId;
    }

    public void setSpId(Integer spId) {
        SpId = spId;
    }

    public Integer getQty() {
        return Qty;
    }

    public void setQty(Integer qty) {
        Qty = qty;
    }

    public Integer getCartId() {
        return CartId;
    }

    public void setCartId(Integer cartId) {
        CartId = cartId;
    }

    public Integer getCartModelId() {
        return CartModelId;
    }

    public void setCartModelId(Integer cartModelId) {
        CartModelId = cartModelId;
    }

    @Override
    public Object getProperty(int i) {

        switch (i) {
            case 0:
                return Actid;
            case 1:
                return SalesmanId;
            case 2:
                return ModalId;
            case 3:
                return SalePrice;
            case 4:
                return TotalPrice;
            case 5:
                return SpId;
            case 6:
                return Qty;
            case 7:
                return CartId;
            case 8:
                return CartModelId;
        }
        return null;
    }

    @Override
    public int getPropertyCount() {
        return 9;
    }

    @Override
    public void setProperty(int i, Object o) {

        switch (i) {
            case 0:
                Actid = Integer.parseInt(o.toString());
                break;
            case 1:
                SalesmanId = Integer.parseInt(o.toString());
                break;
            case 2:
                ModalId = Integer.parseInt(o.toString());
                break;
            case 3:
                SalePrice = Float.parseFloat(o.toString());
                break;
            case 4:
                TotalPrice = Float.parseFloat(o.toString());
                break;
            case 5:
                SpId = Integer.parseInt(o.toString());
                break;
            case 6:
                Qty = Integer.parseInt(o.toString());
                break;
            case 7:
                CartId = Integer.parseInt(o.toString());
                break;
            case 8:
                CartModelId = Integer.parseInt(o.toString());
                break;
            default:
                break;
        }
    }

    @Override
    public void getPropertyInfo(int i, Hashtable hashtable, PropertyInfo propertyInfo) {

        switch (i) {
            case 0:
                propertyInfo.type = propertyInfo.INTEGER_CLASS;
                propertyInfo.name = "Actid";
                break;
            case 1:
                propertyInfo.type = propertyInfo.INTEGER_CLASS;
                propertyInfo.name = "SalesmanId";

                break;
            case 2:
                propertyInfo.type = propertyInfo.INTEGER_CLASS;
                propertyInfo.name = "ModalId";

                break;
            case 3:
                propertyInfo.type = propertyInfo.INTEGER_CLASS;
                propertyInfo.name = "SalePrice";

                break;
            case 4:
                propertyInfo.type = propertyInfo.INTEGER_CLASS;
                propertyInfo.name = "TotalPrice";

                break;
            case 5:
                propertyInfo.type = propertyInfo.INTEGER_CLASS;
                propertyInfo.name = "SpId";

                break;
            case 6:
                propertyInfo.type = propertyInfo.INTEGER_CLASS;
                propertyInfo.name = "Qty";

                break;
            case 7:
                propertyInfo.type = propertyInfo.INTEGER_CLASS;
                propertyInfo.name = "CartId";

                break;
            case 8:
                propertyInfo.type = propertyInfo.INTEGER_CLASS;
                propertyInfo.name = "CartModelId";
                break;
            default:
                break;
        }

    }
}
