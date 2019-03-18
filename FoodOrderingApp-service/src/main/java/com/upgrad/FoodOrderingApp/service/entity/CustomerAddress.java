package com.upgrad.FoodOrderingApp.service.entity;


import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * class used for accessing table customer_address
 * An object of this class is a row in the tables customer_address
 */
@Entity
@Table(name = "customer_address", schema = "public")
@NamedQueries({
        @NamedQuery(name = "addressForCustomer", query = "select c from CustomerAddress as c where c.customer.uuid=:uuid"),
        @NamedQuery(name = "checkCustomerAddress", query = "select c from CustomerAddress as c where c.customer.uuid=:customeruuid and c.address.uuid =:addressuuid")

})
public class CustomerAddress {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "customer_id")
    private CustomerEntity customer;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "address_id")
    private Address address;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public CustomerEntity getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerEntity customer) {
        this.customer = customer;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
