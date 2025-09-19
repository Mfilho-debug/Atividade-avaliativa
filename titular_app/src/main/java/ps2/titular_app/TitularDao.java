package ps2.titular_app;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TitularDao {

    @Autowired
    private TitularRepo titularRepo;

    
    public Iterable<Titular> findAll() {
        return titularRepo.findAll();
    }

    
    public Optional<Titular> findById(long id) {
        return titularRepo.findById(id);
    }

    public Titular save(Titular titular) {
        return titularRepo.save(titular);
    }

    
    public void deleteById(long id) {
        titularRepo.deleteById(id);
    }
}