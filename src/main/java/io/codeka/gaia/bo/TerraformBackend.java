package io.codeka.gaia.bo;

import io.codeka.gaia.bo.backend.Terraform;

public class TerraformBackend {

    private Terraform terraform;

    public Terraform getTerraform() {
        if(terraform == null){
            terraform = new Terraform();
        }
        return terraform;
    }

    public void setTerraform(Terraform terraform) {
        this.terraform = terraform;
    }

}
