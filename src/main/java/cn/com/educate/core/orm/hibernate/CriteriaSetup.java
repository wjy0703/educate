package cn.com.educate.core.orm.hibernate;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import java.util.Map;
import java.util.Set;

/**
 * @author jiangxd
 */
public class CriteriaSetup {
    public void setup(Criteria criteria, Map<String, Object> filter) {
        if (filter != null && !filter.isEmpty()) {
            Set<String> keys = filter.keySet();
            for (String key : keys) {
                String value = (String) filter.get(key);
                if (StringUtils.isNotBlank(value))
                    criteria.add(Restrictions.eq((String) key, value));
            }
        }
    }
}

