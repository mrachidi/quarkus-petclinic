package org.quarkus.samples.petclinic.owner;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class OwnerRepository implements PanacheRepositoryBase<Owner, Long> {

}

