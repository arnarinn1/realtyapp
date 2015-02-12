package is.arnar.realty.systems;

import is.arnar.realty.services.IRealtyService;
import is.arnar.realty.services.ServiceFactory;

public class SystemFacade
{
    public static IRealtyService GetRealtyService()
    {
        return ServiceFactory.GetRestAdapter()
                             .create(IRealtyService.class);
    }
}
