import { Injectable } from '@angular/core';
import {Stop} from "./select-cities/Stop";

@Injectable({
  providedIn: 'root'
})
export class StopService {

  private baseURL : string = "http://localhost:8080/api/stops";

  constructor() { }

  async getStops(): Promise<Stop[]>{
    const url: string = this.baseURL;
    const data: Response = await fetch(url);
    return await data.json() ?? [];
  }
}
