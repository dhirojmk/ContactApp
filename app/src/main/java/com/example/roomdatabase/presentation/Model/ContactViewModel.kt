package com.example.roomdatabase.presentation.Model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.roomdatabase.data.database.Contact
import com.example.roomdatabase.data.database.ContactDataBase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ContactViewModel @Inject constructor(val database: ContactDataBase) : ViewModel() {
    private var isShortedByName = MutableStateFlow(true)

    @OptIn(ExperimentalCoroutinesApi::class)
    private var contacts = isShortedByName.flatMapLatest {
        if (it) {
            database.getDao().getContactsShortedByName()
        } else {
            database.getDao().getContactsOrderedByDate()
        }

    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    val _state = MutableStateFlow(ContactState())
    val state = combine(_state, contacts, isShortedByName) { state, contacts, isShortedByName ->
        state.copy(contacts = contacts)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), ContactState())


    fun changeIsShorting(){
        isShortedByName.value = !isShortedByName.value
    }
    fun saveContact(){
        val contact= Contact(
            id = _state.value.id.value,
            name = _state.value.name.value,
            phone = _state.value.phone.value,
            email = _state.value.email.value,
            isActive = true,
            dateOfCreation = System.currentTimeMillis(),
            image = _state.value.image.value
        )
        viewModelScope.launch {
            database.getDao().upsertContact(contact)
        }
        state.value.id.value = 0
        state.value.name.value = ""
        state.value.phone.value = ""
        state.value.email.value = ""
        state.value.image.value = null

    }

    fun deleteContact(){
        val contact= Contact(
            id = _state.value.id.value,
            name = _state.value.name.value,
            phone = _state.value.phone.value,
            email = _state.value.email.value,
            isActive = true,
            dateOfCreation = System.currentTimeMillis(),
            image = _state.value.image.value
        )
        viewModelScope.launch {
            database.getDao().deleteContact(contact)
        }
        state.value.id.value = 0
        state.value.name.value = ""
        state.value.phone.value = ""
        state.value.email.value = ""
        state.value.dateOfCreation.value=0
        state.value.image.value = null

    }


}