package com.sambet.service.impl;

import com.sambet.dao.LookupDao;
import com.sambet.model.LabelValue;
import com.sambet.model.Role;
import com.sambet.model.TipoRuoli;
import com.sambet.service.LookupManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


/**
 * Implementation of LookupManager interface to talk to the persistence layer.
 *
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 */
@Service("lookupManager")
public class LookupManagerImpl implements LookupManager {
    @Autowired
    LookupDao dao;

    /**
     * {@inheritDoc}
     */
    public List<LabelValue> getAllRoles() {
        List<Role> roles = dao.getRoles();
        List<LabelValue> list = new ArrayList<LabelValue>();
        for (Role role1 : roles) {
            list.add(new LabelValue(role1.getDescription(), role1.getName()));
        }
        return list;
    }
    
    
    public List<LabelValue> getAllTipoRuoli() {
        List<TipoRuoli> tipoRuoli = dao.getTipoRuoli();
        List<LabelValue> list = new ArrayList<LabelValue>();

        for (TipoRuoli tipoRuoli1 : tipoRuoli) {
            list.add(new LabelValue(tipoRuoli1.getDescription(), tipoRuoli1.getName()));
        }
        return list;
    }
}
