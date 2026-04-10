package com.suncontrol.domain.form;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberUpdateForm {

    private Long id;
    private String role;
    private String status;
    private String name;
    private String affiliation;
}
