package repository;


import io.quarkus.hibernate.orm.panache.PanacheRepository;
import model.Exhibit;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ExhibitRepository implements PanacheRepository<Exhibit> { }
