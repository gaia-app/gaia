package io.gaia_app.modules;

import io.gaia_app.modules.bo.TerraformModule;

public interface ModuleService {

    /**
     * Updates the registry details for a module.
     * It tries to compute a project id from an URL, and updates the module.
     * @param module module to update
     */
    void updateRegistryDetails(TerraformModule module);

}
