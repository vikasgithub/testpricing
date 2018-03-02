package pricing;

import com.sap.spe.condmgnt.customizing.IAccess;
import com.sap.spe.condmgnt.customizing.IStep;
import com.sap.spe.condmgnt.finding.userexit.IConditionFindingManagerUserExit;
import com.sap.spe.condmgnt.finding.userexit.RequirementAdapter;

public class TestRequirement extends RequirementAdapter {

	@Override
	public boolean checkRequirement(IConditionFindingManagerUserExit item, IStep step, IAccess access) {
		// TODO Auto-generated method stub
		return super.checkRequirement(item, step, access);
	}

}
