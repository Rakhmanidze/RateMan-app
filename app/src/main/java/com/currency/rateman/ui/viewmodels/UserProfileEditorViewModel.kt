//package com.currency.rateman.ui.viewmodels
//
//import androidx.lifecycle.SavedStateHandle
//import androidx.lifecycle.ViewModel
//import androidx.navigation.toRoute
//import com.currency.rateman.ui.navigation.Routes
//import kotlinx.coroutines.flow.MutableStateFlow
//import kotlinx.coroutines.flow.asStateFlow
//import kotlinx.coroutines.flow.update
//
//data class UserProfileUiState(
//    val name: String,
//    val surname: String,
//    val noOfKids: String
//)
//
//sealed class UserProfileEditorEvent {
//    data class NameChanged(val newName: String): UserProfileEditorEvent()
//    data class SurnameChanged(val newSurname: String): UserProfileEditorEvent()
//    data class NoOfKidsChanged(val newNoOfKids: String): UserProfileEditorEvent()
//}
//
//
//class UserProfileEditorViewModel(
//    private val savedStateHandle: SavedStateHandle
//) : ViewModel() {
//    private val route : Routes.UserProfileEditor = savedStateHandle.toRoute()
//
//    private val _userProfile = MutableStateFlow(
//        UserProfileUiState(
//            route.name,
//            route.surname,
//            route.noOfKids.toString()
//        )
//    )
//
//    val userProfile = _userProfile.asStateFlow()
//
////    var userProfile by mutableStateOf(
////        UserProfileUiState(
////            route.name,
////            route.surname,
////            route.noOfKids.toString()
////        )
////    )
////        private set
//
//    fun onEvent(event: UserProfileEditorEvent): Unit {
//        when (event) {
//            is UserProfileEditorEvent.NameChanged -> {
//                _userProfile.value = _userProfile.value.copy(
//                    name = event.newName
//                )
//            }
//            is UserProfileEditorEvent.SurnameChanged -> {
//                _userProfile.update {
//                    _userProfile.value.copy(
//                        surname = event.newSurname
//                    )
//                }
//            }
//            is UserProfileEditorEvent.NoOfKidsChanged -> {
//                _userProfile.value = _userProfile.value.copy(
//                    noOfKids = event.newNoOfKids
//                )
//            }
//        }
//    }
//
////    private val userProfile = savedStateHandle.toRoute<Routes.UserProfileEditor>().userProfile
////
////    private val _profileName = mutableStateOf(savedStateHandle["profileName"] ?: userProfile.name)
////    //val profileName: State<String> = _profileName
////    val profileName by _profileName
////
////    fun setProfileName(newName: String) {
////        _profileName.value = newName
////        savedStateHandle["profileName"] = newName
////    }
//}