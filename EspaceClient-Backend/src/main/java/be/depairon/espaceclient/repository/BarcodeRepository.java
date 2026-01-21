package be.depairon.espaceclient.repository;

import be.depairon.espaceclient.entity.Barcode;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BarcodeRepository extends CrudRepository<Barcode, Long> {
    // TODO vérifier avec NHO si la requete uniquement sur porteurid est ok
    List<Barcode> findAllByPorteurIdAndFlag(long porteurId, String flag);

    List<Barcode> findAllByPorteurIdAndArticleArtIinAndFlag(Long porteurId, Integer artIin, String flag);
}
