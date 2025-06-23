package com.example.roomdatabase.presentation.screens

import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.roomdatabase.presentation.Model.ContactState
import com.example.roomdatabase.presentation.Model.ContactViewModel
import com.example.roomdatabase.presentation.Navigation.Routs

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    navHostController: NavHostController,
    state: ContactState,
    viewModel: ContactViewModel
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Contact Keeper")
                },
                actions = {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.List, contentDescription = "Sort",
                        modifier = Modifier.padding(7.dp).clickable {
                            viewModel.changeIsShorting()
                        })

                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navHostController.navigate(Routs.AddEdit.Route)
                },
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add")

            }
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        )
        {
            LazyColumn {
                items(state.contacts) { contact ->
                    val bitmap = contact.image?.let {
                        BitmapFactory.decodeByteArray(it, 0, it.size)
                    }?.asImageBitmap()
                    ContactCard(
                        name = contact.name,
                        phone = contact.phone,
                        email = contact.email,
                        dateOfCreation = contact.dateOfCreation.toString(),
                        image = bitmap,
                        imageByteArray = contact.image,
                        id = contact.id,
                        viewModel = viewModel,
                        state = state,
                        navHostController = navHostController

                    )
                }
            }

        }

    }
}

@Composable
fun ContactCard(
    name: String,
    phone: String,
    email: String,
    dateOfCreation: String,
    image: ImageBitmap?,
    imageByteArray: ByteArray?,
    id: Int,
    viewModel: ContactViewModel,
    state: ContactState,
    navHostController: NavHostController


) {
    val context = LocalContext.current
    Card(
        onClick = {
            state.id.value = id
            state.name.value = name
            state.phone.value = phone
            state.email.value = email
            state.image.value = imageByteArray
            state.dateOfCreation.value = dateOfCreation.toLong()
            navHostController.navigate(Routs.AddEdit.Route)

        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clip(RoundedCornerShape(12.dp))
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)

        ) {
            if (image != null) {
                Image(
                    bitmap = image,
                    contentDescription = "contact image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(64.dp)
                        .clip(shape = CircleShape)
                )
            } else {
                Icon(
                    imageVector = Icons.Default.Person, contentDescription = "Add",
                    modifier = Modifier
                        .size(64.dp)
                        .clip(shape = CircleShape)
                        .background(MaterialTheme.colorScheme.primaryContainer)
                        .padding(16.dp),
                    tint = MaterialTheme.colorScheme.onPrimaryContainer

                )
            }

            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(
                    text = name,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = phone,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = email,
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )

            }
            Spacer(modifier = Modifier.width(12.dp))
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                IconButton(
                    onClick = {
                        state.id.value = id
                        state.name.value = name
                        state.phone.value = phone
                        state.email.value = email
                        state.dateOfCreation.value = dateOfCreation.toLong()
                        viewModel.deleteContact()
                    }

                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete",
                        tint = MaterialTheme.colorScheme.error
                    )
                }
                IconButton(
                    onClick = {
                        val intent = Intent(Intent.ACTION_DIAL)
                        intent.data = Uri.parse("tel:$phone")
                        context.startActivity(intent)
                    }

                ) {
                    Icon(
                        imageVector = Icons.Default.Call,
                        contentDescription = "Call",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }

            }
        }
    }

}