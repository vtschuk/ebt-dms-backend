package de.ass73.ebt.dms.backend.services

import de.ass73.ebt.dms.backend.entities.CertificateEntity
import de.ass73.ebt.dms.backend.models.CertificateModel
import de.ass73.ebt.dms.backend.repository.CertRepo
import de.ass73.ebt.dms.backend.services.exceptions.BadServiceCallException
import org.modelmapper.ModelMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.stream.Collectors

@Service
class CertificateService(
    @Autowired
    private val certRepo: CertRepo,

    @Autowired
    private val modelMapper: ModelMapper

) {
    /* used by group: read, edit, admin */
    fun getAllCertifcates(username: String): List<CertificateModel> {
        var entities = certRepo.findAll()
        var models = entities
            .stream()
            .map { cert -> modelMapper.map(cert, CertificateModel::class.java) }
            .collect(Collectors.toList())
        return models
    }

    /* used by group: read, edit, admin */
    fun getCertificateById(id: Long, username: String): CertificateModel {
        return certRepo.findById(id)
            .map { cert -> modelMapper.map(cert, CertificateModel::class.java) }
            .orElseThrow { BadServiceCallException("$id:no such id found") }
    }

    /* used by group:  admin */
    fun createCertificate(certificateModel: CertificateModel, username: String): CertificateModel {
        val certificateEntity: CertificateEntity =
            modelMapper.map(certificateModel, CertificateEntity::class.java)
        val entityToSave: CertificateEntity = certRepo.save(certificateEntity)
        return modelMapper.map(entityToSave, CertificateModel::class.java)
    }

    /* used by group:  admin */
    fun saveCertificate(id: Long, certificateModel: CertificateModel, username: String): CertificateModel {
        return if (certRepo.existsById(id)) {
            val certificateEntity: CertificateEntity =
                modelMapper.map(certificateModel, CertificateEntity::class.java)
            modelMapper.map(certRepo.save(certificateEntity), CertificateModel::class.java)
        } else {
            throw BadServiceCallException("$id:no such id found")
        }
    }

    /* used by group: admin */
    fun delete(id: Long, username: String) {
        certRepo.deleteById(id)
    }
}
