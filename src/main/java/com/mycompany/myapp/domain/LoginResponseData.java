package com.mycompany.myapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A LoginResponseData.
 */
@Entity
@Table(name = "login_response_data")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class LoginResponseData implements Serializable {

    private static final long serialVersionUID = 1L;

    public LoginResponseData() {
    }

    public LoginResponseData(Boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "success")
    private Boolean success;

    @Column(name = "message")
    private String message;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isSuccess() {
        return success;
    }

    public LoginResponseData success(Boolean success) {
        this.success = success;
        return this;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public LoginResponseData message(String message) {
        this.message = message;
        return this;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        LoginResponseData loginResponseData = (LoginResponseData) o;
        if (loginResponseData.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), loginResponseData.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "LoginResponseData{" +
            "id=" + getId() +
            ", success='" + isSuccess() + "'" +
            ", message='" + getMessage() + "'" +
            "}";
    }
}
