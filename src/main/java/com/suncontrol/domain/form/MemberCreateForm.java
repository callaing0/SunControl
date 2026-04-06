package com.suncontrol.domain.form;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberCreateForm {

    private String userId;
    private String name;
    private String affiliation;
    private String role;
}
