package one.digitalinnovation.parking.service;

import one.digitalinnovation.parking.controller.ParkingNotFoundException;
import one.digitalinnovation.parking.model.Parking;
import one.digitalinnovation.parking.repository.ParkingRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ParkingService {
    //private static Map<String, Parking> parkingMap = new HashMap<>();
    private final ParkingRepository parkingRepository;

    public ParkingService(ParkingRepository parkingRepository) {
        this.parkingRepository = parkingRepository;
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<Parking> findAll() {
        /*
        return parkingMap
                .values()
                .stream()
                .collect(Collectors.toList());

         */
        return parkingRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Parking findById(String id) {
        /*
        Parking parking = parkingMap.get(id);
        if(parking == null) {
            throw new ParkingNotFoundException(id);
        }
        return parkingMap
                .get(id);

         */
        return parkingRepository
                .findById(id)
                .orElseThrow(() -> new ParkingNotFoundException(id));
    }

    @Transactional
    public Parking create(Parking parkingCreate) {
        String uuid = getUUID();
        parkingCreate.setId(uuid);
        parkingCreate.setEntryDate(LocalDateTime.now());
        //parkingMap.put(uuid, parkingCreate);
        parkingRepository.save(parkingCreate);
        return parkingCreate;
    }

    @Transactional
    public Parking update(String id, Parking parkingCreate) {
        Parking parking = findById(id);
        parking.setColor(parkingCreate.getColor());
        parking.setState(parkingCreate.getState());
        parking.setModel(parkingCreate.getModel());
        parking.setLicense(parkingCreate.getLicense());
        //parkingMap.replace(id, parking);
        parkingRepository.save(parking);
        return parking;
    }

    @Transactional
    public void delete(String id) {
        findById(id);
        /*
        parkingMap
                .remove(id);

         */
        parkingRepository.deleteById(id);
    }

    public Parking checkout(String id) {
        // recuperar o estacionamento
        // atualizar data de saida
        // calcular o valor
        Parking parking = findById(id);
        parking.setExitDate(LocalDateTime.now());
        parking.setBill(ParkingCheckOut.getBill(parking));
        parkingRepository.save(parking);
        return parking;
    }

    private static String getUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
