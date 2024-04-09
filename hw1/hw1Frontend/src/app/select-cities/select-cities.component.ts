import {Component, inject, model} from '@angular/core';
import {Stop} from "./Stop";
import {StopService} from "../stop.service";
import {of} from "rxjs";
import {NgForOf} from "@angular/common";
import {TripService} from "../trip.service";
import {TripDetails} from "./TripDetails";
import {FormsModule} from "@angular/forms";
import { NgbCalendar, NgbDatepickerModule, NgbDateStruct } from '@ng-bootstrap/ng-bootstrap';
import { JsonPipe } from '@angular/common';
import {CurrencyService} from "../currency.service";
import {CurrencyDetails} from "./CurrencyDetails";

@Component({
  selector: 'app-select-cities',
  standalone: true,
  imports: [ NgForOf, FormsModule, NgbDatepickerModule, JsonPipe],
  templateUrl: './select-cities.component.html',
  styleUrl: './select-cities.component.css'
})
export class SelectCitiesComponent {
  stops : Stop[] = []
  trips: TripDetails[] = []
  currencies: String[] = []
  originStop: Stop | null = null;
  destinationStop: Stop | null = null;
  selectedCurrency: String | null = null;
  stopService: StopService = inject(StopService)
  tripService: TripService = inject(TripService)
  currencyService: CurrencyService = inject(CurrencyService)
  today = inject(NgbCalendar).getToday();
  departure_date: NgbDateStruct = this.today;

  constructor() {
    console.log("today", this.today)
    console.log("model", this.departure_date)
    this.stopService.getStops().then((stops: Stop[]) => {
      console.log("stops", stops)
      this.stops = stops;
    })
    this.currencyService.getAllCurrencies().then((currencies: CurrencyDetails[]) => {
      this.currencies = Object.keys(currencies);
      console.log("currencies", this.currencies)
    })
  }

  onSelectOrigin(stop: Stop){
    console.log("origin", stop)
    this.originStop = stop;
  }

  onSelectDestination(stop: Stop){
    console.log("destination", stop)
    this.destinationStop = stop;
  }

  onSelectCurrency(currency: String){
    console.log("currency", currency)
    this.selectedCurrency = currency;
  }

  searchTrips(){
    if (this.originStop!=null && this.destinationStop!=null){
      this.tripService.getTripsBetweenCities(this.originStop.id, this.destinationStop.id, "EUR", "2024-06-02")
        .then((trips: TripDetails[]) => {
          this.trips = trips;
          console.log(trips)
        })
    }
  }

  protected readonly of = of;
}
