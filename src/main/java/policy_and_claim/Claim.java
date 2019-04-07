package policy_and_claim;

import org.jboss.resteasy.annotations.jaxrs.FormParam;

import java.io.Serializable;

public class Claim implements Serializable {
    @FormParam("policyNo")
    public String policyNo;
    @FormParam("detail")
    public String detail;
    @FormParam("real_name")
    public String real_name;
    @FormParam("claim_date")
    public String claim_date;
    @FormParam("loss_date")
    public String loss_date;
    public String ClaimNo;
    public String feedback;
    public Claim() {

    }
}