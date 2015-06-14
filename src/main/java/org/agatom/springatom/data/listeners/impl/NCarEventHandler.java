package org.agatom.springatom.data.listeners.impl;

import org.agatom.springatom.data.model.car.NCar;
import org.agatom.springatom.data.model.vin.VinNumber;
import org.agatom.springatom.data.repo.repositories.vin.VinNumberRepository;
import org.agatom.springatom.data.vin.decoder.VinDecoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.HandleAfterCreate;
import org.springframework.data.rest.core.annotation.HandleAfterSave;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RepositoryEventHandler(NCar.class)
public class NCarEventHandler {
  @Autowired
  private VinDecoder          vinDecoder          = null;
  @Autowired
  private VinNumberRepository vinNumberRepository = null;

  @HandleAfterSave
  public void handleAfterSave(final NCar car) {
//    this.associate(car);
  }

  @HandleAfterCreate
  public void handleAfterCreate(final NCar car) {
//    this.associate(car);
  }

  // TODO failed to work
  protected void associate(final NCar car) {
    final Optional<VinNumber> byCar = this.vinNumberRepository.findByCar(car);
    final String vinNumber = car.getVinNumber();

    VinNumber decoded = this.vinDecoder.decode(vinNumber);

    if (byCar.isPresent()) {
      final VinNumber vinNumberOld = byCar.get();

      vinNumberOld.setData(decoded.getData());
      vinNumberOld.setNumber(decoded.getNumber());

      decoded = vinNumberOld;
    }

    this.vinNumberRepository.save(decoded.setCar(car));
  }

}
