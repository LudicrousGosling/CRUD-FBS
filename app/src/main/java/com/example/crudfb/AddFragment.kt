package com.example.crudfb

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import com.example.crudfb.databinding.FragmentAddBinding
import com.example.crudfb.models.Contacts
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class AddFragment : Fragment() {
    private var _binding : FragmentAddBinding? = null
    private val binding get() = _binding!!

    private lateinit var firebaseRef : DatabaseReference
    private lateinit var storageRef : StorageReference

    private var uri: Uri? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddBinding.inflate(inflater, container, false)
        firebaseRef  = FirebaseDatabase.getInstance().getReference("contacts")
        storageRef = FirebaseStorage.getInstance().getReference("Images")

        binding.btnSend.setOnClickListener {
            saveData()
        }

        val picKImage = registerForActivityResult(ActivityResultContracts.GetContent()) {
            binding.imgAdd.setImageURI(it)
            if (it != null) {
                uri = it
            }
        }

        binding.btnPickImage.setOnClickListener {
            picKImage.launch("image/*")
        }

        return binding.root
    }

    private fun saveData() {
        val name = binding.edtName.text.toString()
        val phone = binding.edtPhone.text.toString()

        if (name.isEmpty()) binding.edtName.error = "Write a name"
        if (phone.isEmpty()) binding.edtPhone.error = "Write a phone"

        val contactId = firebaseRef.push().key!!
        var contacts : Contacts

        uri?.let {
            storageRef.child(contactId).putFile(it)
                .addOnSuccessListener { task->
                    task.metadata!!.reference!!.downloadUrl
                        .addOnSuccessListener { url ->

                            Toast.makeText(context, "Image stored succesfully", Toast.LENGTH_SHORT).show()
                            val imgUrl = url.toString()

                            contacts = Contacts(contactId, name, phone, imgUrl)

                            firebaseRef.child(contactId).setValue(contacts)
                                .addOnCompleteListener {
                                    Toast.makeText(context, "data stored succesfully", Toast.LENGTH_SHORT).show()
                                }
                                .addOnFailureListener{
                                    Toast.makeText(context, "error ${it.message}", Toast.LENGTH_SHORT).show()
                                }

                        }
                }
        }


    }


}