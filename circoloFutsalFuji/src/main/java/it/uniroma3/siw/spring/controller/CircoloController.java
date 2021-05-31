package it.uniroma3.siw.spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import it.uniroma3.siw.spring.model.Campo;
import it.uniroma3.siw.spring.model.Prenotazione;
import it.uniroma3.siw.spring.model.Utente;
import it.uniroma3.siw.spring.service.CampoService;

@Controller
public class CircoloController {
	
	@Autowired
	private CampoService campoService;
	
	@RequestMapping(value = "/campi", method = RequestMethod.GET)
	public String getCampi(Model model) {
		model.addAttribute("campi", this.campoService.tutti());
		return "campi.html";
	}
	
	@RequestMapping(value = "/campo/{id}", method = RequestMethod.GET)
	public String getCampo(@PathVariable Long id, Model model) {
		model.addAttribute("campo", this.campoService.campoPerId(id));
		return "campo.html";
	}
	
	@RequestMapping(value = "/gestore", method = RequestMethod.GET)
	public String getGestore(Model model) {
		return "gestore.html";
	}
	
	@RequestMapping(value = "/gestoreCampo", method = RequestMethod.GET)
	public String getGestoreCampo(Model model) {
		return "gestoreCampo.html";
	}
	
	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public String getHomePage(Model model) {
		return "index.html";
	}

	@RequestMapping(value = "/prenotaUnCampo", method = RequestMethod.GET)
	public String iniziaPrenotazione(Model model) {
		return this.getCampi(model);
	}
	
	
	@RequestMapping(value ="/prenota", method = RequestMethod.GET)
	public String prenotaCampo(@ModelAttribute("campo") Campo campo, Model model) {
		model.addAttribute(campo);
		model.addAttribute("prenotazione", new Prenotazione());
		model.addAttribute("utente", new Utente());
		return "prenotazione.html";
	}
}
