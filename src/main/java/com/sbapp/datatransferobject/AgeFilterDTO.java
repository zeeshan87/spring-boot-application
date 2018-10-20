package com.sbapp.datatransferobject;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sbapp.domainvalue.Operator;
import com.sbapp.domainvalue.Sort;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(value="AgeFilterDTO", description="Age Filter Model for the API documentation")
public class AgeFilterDTO
{
	@NotNull(message = "Operator can not be null!")
	@ApiModelProperty(notes = "Filter Operator: lt – less than\n"
			+ " lte – less than equal\n "
			+ "gt – greater than\n "
			+ "gte – greater than equal\n "
			+ "eq – equal\n "
			+ "ne – not equal", required = true)
    private Operator operator;

	@Min(value = 18, message = "Value can not be less than 18!")
    @ApiModelProperty(notes = "Age value to be compared", required = true, allowableValues = "range[18,]")
    private int value;

	@NotNull(message = "Sort can not be null!")
	@ApiModelProperty(notes = "Sort Operator: asc – Ascending\n"
			+ " desc – Descending", required = true)
    private Sort sort;

    private AgeFilterDTO() {
    }

	public AgeFilterDTO(Operator operator, int value, Sort sort) {
		this.operator = operator;
		this.value = value;
		this.sort = sort;
	}

	public Operator getOperator() {
		return operator;
	}

	public void setOperator(Operator operator) {
		this.operator = operator;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public Sort getSort() {
		return sort;
	}

	public void setSort(Sort sort) {
		this.sort = sort;
	}    
}
