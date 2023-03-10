package com.openclassrooms.realestatemanager.ui.add_or_modify_real_estate

data class AddOrModifyRealEstateAgentSpinnerItemViewState(
    val agentIdInCharge: Int,
    val agentNameInCharge: String,
    val agentPhoto: String,
) {
    override fun toString(): String {
        return "AddOrModifyRealEstateAgentSpinnerItemViewState(agentIdInCharge=$agentIdInCharge, agentNameInCharge='$agentNameInCharge', agentPhoto='$agentPhoto')"
    }
}
