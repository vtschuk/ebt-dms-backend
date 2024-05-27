package de.ass73.ebt.dms.backend.services.image

import de.ass73.ebt.dms.backend.entities.image.PhotoImage
import de.ass73.ebt.dms.backend.models.image.PhotoUploadResponse
import de.ass73.ebt.dms.backend.repository.image.UploadPhotoImageRepository
import jakarta.transaction.Transactional
import org.apache.coyote.BadRequestException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.IOException
import java.util.function.Consumer

@Service
class UploadPhotoImageService(
    @Autowired
    private val uploadPhotoImageRepository: UploadPhotoImageRepository
) {

    //todo implement image compressor
    //@Value("$(ass73.personalakte.aktivateimagecompressor)")
    //private boolean imageCompressor;
    @Throws(IOException::class)
    fun uploadImage(fileId: Long, file: MultipartFile, username: String): PhotoUploadResponse {
        // delete all images with this fileId
        val images = uploadPhotoImageRepository.findByFileId(fileId)
        images.stream().forEach(Consumer { image: PhotoImage? ->
            if (image != null) {
                uploadPhotoImageRepository.delete(image)
            }
        })
        var photoImage = file.originalFilename?.let {
            file.contentType?.let { it1 ->
                PhotoImage(
                    0, fileId, it,
                    it1, file.bytes
                )
            }
        }
        if (photoImage != null) {
            uploadPhotoImageRepository.save(photoImage)
        }
        return PhotoUploadResponse()
    }

    @Transactional
    @Throws(BadRequestException::class)
    fun getImageByFileId(fileId: Long, username: String): PhotoImage {
        val images = uploadPhotoImageRepository.findByFileId(fileId)
        if(images.isEmpty()) {
            return PhotoImage(0, fileId, "", "", byteArrayOf())
        } else {
            return images.get(0)!!
        }

    }
}
