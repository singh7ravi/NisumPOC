package com.example.nisumpoc.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.nisumpoc.R
import com.example.nisumpoc.domain.utils.ApiState
import com.example.nisumpoc.domain.utils.FilterType
import com.example.nisumpoc.domain.utils.Utils
import com.example.nisumpoc.domain.utils.filterCategories
import com.example.nisumpoc.presentation.FilterBottomSheet
import com.example.nisumpoc.presentation.ShowLoading
import com.example.nisumpoc.presentation.SpacerWidth
import com.example.nisumpoc.presentation.viewModel.MainViewModel
import coil.compose.rememberImagePainter
import com.example.nisumpoc.data.model.User
import com.example.nisumpoc.presentation.navigation.UserDetails
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashBoardScreen(
    navHostController: NavHostController,
    mainViewModel: MainViewModel = hiltViewModel()
) {
    var functionCalled by remember { mutableStateOf(false) }
    var showBottomSheet by remember { mutableStateOf(false) }
    val selectedCategories = remember { mutableStateListOf<String>() }
    val filters = remember { mutableStateListOf<FilterType>() }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.app_name),
                        style = TextStyle(
                            color = Color.White,
                            fontSize = 25.sp,
                            fontWeight = FontWeight.Bold
                        )
                    )
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = MaterialTheme.colorScheme.primary)
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                showBottomSheet = true
                selectedCategories.clear()
                filters.clear()
            }) {
                Icon(Icons.Default.FilterList, contentDescription = "Filter")
            }
        }
    ) {
        Box(modifier = Modifier.padding(it)) {
            CallApi(mainViewModel, navHostController)
            if (showBottomSheet) {
                FilterBottomSheet(
                    filterCategories = filterCategories,
                    onCategorySelected = { category ->
                        if (category in selectedCategories) {
                            selectedCategories.remove(category)
                        } else {
                            selectedCategories.add(category)
                        }
                        functionCalled = true
                    },
                    onDismissRequest = { showBottomSheet = false }
                )
            }

            LaunchedEffect(functionCalled) {
                if (functionCalled) {
                    mainViewModel.getFilterListByOption(
                        Utils.filterUsersByGender(selectedCategories.toList())
                    )
                    functionCalled = false // Reset the flag
                }
            }
        }
    }
}

@Composable
fun CallApi(mainViewModel: MainViewModel, navHostController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 10.dp, vertical = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SpacerWidth(30.dp)

        LaunchedEffect(Unit) {
            mainViewModel.getRandomUserApi()
        }
        when (val result = mainViewModel.response.value) {
            is ApiState.Loading -> {
                ShowLoading()
            }

            is ApiState.Success -> {
                if (result.data.code() == 200) {
                    result.data.body()?.let {
                        ShowListPreview(mainViewModel, it.results, navHostController)
                    }
                } else {
                    Text(
                        text = "${stringResource(id = R.string.something_went_wrong)} ${result.data.code()}",
                        style = TextStyle(
                            fontSize = 18.sp,
                            color = Color.Red,
                            fontWeight = FontWeight.Normal
                        )
                    )
                }
            }

            is ApiState.Failure -> {
                Text(
                    text = "${result.msg}",
                    style = TextStyle(
                        fontSize = 18.sp,
                        color = Color.Red,
                        fontWeight = FontWeight.Normal
                    )
                )
            }

            is ApiState.Empty -> {
                //This is Ideal Case
            }
        }

    }
}

@Composable
fun UserCard(user: User, onClick: () -> Unit){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(modifier = Modifier
            .padding(16.dp)
            .clickable { onClick() }) {
            Image(
                painter = rememberImagePainter(user.picture.medium),//user.picture.large),
                contentDescription = "Profile Image",
                modifier = Modifier
                    .size(60.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
            SpacerWidth(20.dp)
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "${user.name.title}. ${user.name.first} ${user.name.last}",
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = "${user.location.street.number} ${user.location.street.name}, ${user.location.city}, ${user.location.country}",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}

@Composable
fun MyApp(content: @Composable () -> Unit) {
    MaterialTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            content()
        }
    }
}

@Composable
fun ShowListPreview(mainViewModel: MainViewModel, list: List<User>, navHostController: NavHostController) {
    val viewState by mainViewModel.viewState.collectAsState()
    val searchItem = remember { mutableStateOf("") }
    MyApp {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            item {
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                        .background(color = Color.White),
                    value = searchItem.value,
                    onValueChange = { searchText ->
                        searchItem.value = searchText
                        mainViewModel.getFilterListByName(list, searchText)

                    },
                    leadingIcon = {
                        Icon(imageVector = Icons.Default.Search, contentDescription = "")
                    })
            }
            items(items = viewState.filterList) { item ->
                item?.let {
                    UserCard(user = it){
                        val userJson = URLEncoder.encode(it.toJson(), StandardCharsets.UTF_8.toString())
                        navHostController.navigate("$UserDetails/$userJson")
                    }
                }
            }
        }
    }
}
