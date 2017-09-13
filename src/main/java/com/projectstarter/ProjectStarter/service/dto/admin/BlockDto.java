package com.projectstarter.ProjectStarter.service.dto.admin;

import com.projectstarter.ProjectStarter.service.dto.Dto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BlockDto implements Dto {
    public String[] emails;
}
