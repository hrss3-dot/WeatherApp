package com.example.weatherapp.ui

import android.app.Activity
import android.widget.Toast
import androidx.benchmark.traceprocessor.Row
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.weatherapp.R
import com.example.weatherapp.model.City
import com.example.weatherapp.model.MainViewModel
import com.example.weatherapp.model.Weather
import com.example.weatherapp.ui.nav.Route
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.outlined.Notifications

@Composable
fun ListPage(modifier: Modifier = Modifier,
            viewModel: MainViewModel
) {
//    val cityList = remember { getCities().toMutableStateList() }
    val cityList = viewModel.cities

    // val activity = LocalActivity.current as Activity // Para os Toasts
    val context = LocalContext.current
    val activity = context as Activity

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        items(items = cityList, key = { it.name } ) { city ->
            CityItem(city = city, weather = viewModel.weather(city.name), onClose = {
                /* TO DO */
                viewModel.remove(city)
                Toast.makeText(activity, "Cidade Removida", Toast.LENGTH_LONG).show();
            }, onClick = {
                /* TO DO */
                viewModel.city = city.name
                viewModel.page = Route.Home

                Toast.makeText(activity, "Cidade selecionada", Toast.LENGTH_LONG).show();

            })
        }
    }
}




@Composable
fun CityItem(
    city: City,
    weather: Weather,
    onClick: () -> Unit,
    onClose: () -> Unit,
    modifier: Modifier = Modifier
) {
    val desc = if (weather == Weather.LOADING) "Carregando clima..." else weather.desc


    val icon = if (city.isMonitored) {
        Icons.Filled.Notifications
    } else {
        Icons.Outlined.Notifications
    }

    Row(
        modifier = modifier.fillMaxWidth().padding(8.dp).clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = weather.imgUrl,
            modifier = modifier.size(75.dp),
            error = painterResource(id = R.drawable.loading),
            contentDescription = "Imagem"
        )
        Spacer(modifier = Modifier.size(12.dp))
        Column(modifier = modifier.weight(1f)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(modifier = Modifier,
                    text = city.name,
                    fontSize = 24.sp)

                Spacer(modifier = Modifier.size(8.dp))


                Icon(
                    imageVector = icon,
                    contentDescription = "Status de Monitoramento",
                    modifier = Modifier.size(24.dp)
                )
            }

            Text(modifier = Modifier,
                text = desc,
                fontSize = 16.sp)
        }
        IconButton(onClick = onClose) {
            Icon(Icons.Filled.Close, contentDescription = "Close")
        }
    }
}
