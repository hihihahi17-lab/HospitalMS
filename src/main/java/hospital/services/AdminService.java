package hospital.services;

import hospital.exceptions.EntityNotFoundException;
import hospital.models.Admin;
import hospital.persistence.AdminRepository;

import java.util.List;

public class AdminService {

    private final List<Admin> admins;
    private final AdminRepository repository;

    public AdminService() {
        this.repository = new AdminRepository();
        this.admins = repository.loadAll();
    }

    public List<Admin> getAllAdmins() {
        return admins;
    }

    public Admin findById(int id) throws EntityNotFoundException {
        for (Admin a : admins) {
            if (a.getId() == id) return a;
        }
        throw new EntityNotFoundException("Admin", String.valueOf(id));
    }
}