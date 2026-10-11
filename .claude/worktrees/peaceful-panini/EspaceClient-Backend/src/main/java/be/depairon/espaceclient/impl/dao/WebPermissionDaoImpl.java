package be.depairon.espaceclient.impl.dao;

import be.depairon.espaceclient.dao.GenericDao;
import be.depairon.espaceclient.dao.WebPermissionDao;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class WebPermissionDaoImpl extends GenericDao implements WebPermissionDao {
    @Override
    public List<Object[]> getPermission(String profile) {
        Session session = getSession();
        String query = "select 0       as WEBPROFILE_ID, " +
                "       '', " +
                "       '', " +
                "       0, " +
                "       role.ID as WEBROLE_ID, " +
                "       role.ROLE, " +
                "       role.COMMENT," +
                "       0 as ACCESS " +
                "from YGDEPAIRON.WEBROLE role " +
                "where ID not in (select role.ID " +
                "                 from YGDEPAIRON.WEBPROFILE profile " +
                "                          join YGDEPAIRON.WEBPROFILEROLE wpr on wpr.WEBPROFILEID = profile.ID " +
                "                          join YGDEPAIRON.WEBROLE role on role.ID = wpr.WEBROLEID " +
                "                 where profile.NAME = :profile) " +
                "union " +
                "select profile.id as WEBPROFILE_ID, " +
                "       profile.name, " +
                "       profile.description, " +
                "       profile.noextacces, " +
                "       role.ID    as WEBROLE_ID, " +
                "       role.ROLE, " +
                "       role.COMMENT," +
                "       1 as ACCESS " +
                "from YGDEPAIRON.WEBPROFILE profile " +
                "         join YGDEPAIRON.WEBPROFILEROLE wpr on wpr.WEBPROFILEID = profile.ID " +
                "         join YGDEPAIRON.WEBROLE role on role.ID = wpr.WEBROLEID " +
                "where profile.NAME = :profile " +
                "order by WEBPROFILE_ID desc";
        return session.createNativeQuery(query)
                .setParameter("profile", profile)
                .getResultList();
    }

    @Override
    public List<Object[]> getAllUserPermissions(String userId) {
        Session session = getSession();
        String query = "    select role.ROLE, 0 as ACCESS, role.COMMENT as COMMENT " +
                "    from YGDEPAIRON.WEBROLE role " +
                "    where role.ROLE not in (select wur.ROLE " +
                "            from YGDEPAIRON.WEBUSERROLE wur " +
                "            join YGDEPAIRON.WEBROLE wr on wr.ROLE = wur.ROLE " +
                "            where wur.USERID = :userid) " +
                "    union " +
                "    select wur.ROLE, 1 as ACCESS, wr.COMMENT as COMMENT " +
                "    from YGDEPAIRON.WEBUSERROLE wur " +
                "    join YGDEPAIRON.WEBROLE wr on wr.ROLE = wur.ROLE " +
                "    where wur.USERID = :userid" +
                "    order by COMMENT ";
        return session.createNativeQuery(query)
                .setParameter("userid", userId)
                .getResultList();
    }


    @Override
    public List<Object[]> getUserPermission(String userId) {
        Session session = getSession();
        String query = "select role.ROLE, case when webuserrole.ID is not null then 1 else 0 end as ACCESS, role.COMMENT " +
                "from YGDEPAIRON.WEBUSER webuser " +
                "         join YGDEPAIRON.WEBPROFILE profile on profile.NAME = webuser.PROFILE " +
                "         join YGDEPAIRON.WEBPROFILEROLE profilerole on profile.ID = profilerole.WEBPROFILEID " +
                "         join YGDEPAIRON.WEBROLE role on role.ID = profilerole.WEBROLEID " +
                "         left join YGDEPAIRON.WEBUSERROLE webuserrole " +
                "                   on webuserrole.USERID = webuser.USERID and webuserrole.ROLE = role.ROLE " +
                "where webuser.USERID = :userid " +
                "order by role.comment ";
        return session.createNativeQuery(query)
                .setParameter("userid", userId)
                .getResultList();
    }
}
