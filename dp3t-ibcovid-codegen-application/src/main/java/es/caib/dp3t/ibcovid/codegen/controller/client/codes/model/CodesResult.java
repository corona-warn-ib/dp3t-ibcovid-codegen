package es.caib.dp3t.ibcovid.codegen.controller.client.codes.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * CodesResult
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2020-08-10T16:59:47.385+02:00[Europe/Paris]")
public class CodesResult   {
  @JsonProperty("validUntil")
  @org.springframework.format.annotation.DateTimeFormat(iso = org.springframework.format.annotation.DateTimeFormat.ISO.DATE_TIME)
  private Date validUntil;

  @JsonProperty("signature")
  private String signature;

  @JsonProperty("codes")
  @Valid
  private List<String> codes = new ArrayList<>();

  public CodesResult validUntil(Date validUntil) {
    this.validUntil = validUntil;
    return this;
  }

  /**
   * Date in UTC until codes are valid
   * @return validUntil
  */
  @ApiModelProperty(required = true, value = "Date in UTC until codes are valid")
  @NotNull

  @Valid
@Pattern(regexp="dd/MM/yyyy HH:mm:ss.SSS z") 
  public Date getValidUntil() {
    return validUntil;
  }

  public void setValidUntil(Date validUntil) {
    this.validUntil = validUntil;
  }

  public CodesResult signature(String signature) {
    this.signature = signature;
    return this;
  }

  /**
   * Server signature so client applications can verify the response
   * @return signature
  */
  @ApiModelProperty(required = true, value = "Server signature so client applications can verify the response")
  @NotNull


  public String getSignature() {
    return signature;
  }

  public void setSignature(String signature) {
    this.signature = signature;
  }

  public CodesResult codes(List<String> codes) {
    this.codes = codes;
    return this;
  }

  public CodesResult addCodesItem(String codesItem) {
    this.codes.add(codesItem);
    return this;
  }

  /**
   * List of codes
   * @return codes
  */
  @ApiModelProperty(required = true, value = "List of codes")
  @NotNull


  public List<String> getCodes() {
    return codes;
  }

  public void setCodes(List<String> codes) {
    this.codes = codes;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CodesResult codesResult = (CodesResult) o;
    return Objects.equals(this.validUntil, codesResult.validUntil) &&
        Objects.equals(this.signature, codesResult.signature) &&
        Objects.equals(this.codes, codesResult.codes);
  }

  @Override
  public int hashCode() {
    return Objects.hash(validUntil, signature, codes);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CodesResult {\n");

    sb.append("    validUntil: ").append(toIndentedString(validUntil)).append("\n");
    sb.append("    signature: ").append(toIndentedString(signature)).append("\n");
    sb.append("    codes: ").append(toIndentedString(codes)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

