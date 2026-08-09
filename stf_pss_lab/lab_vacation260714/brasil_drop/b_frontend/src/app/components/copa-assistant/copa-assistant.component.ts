import { Component } from '@angular/core';
import { CopaAssistantService } from '../../services/copa-assistant.service';

interface ChatMessage { sender: 'user' | 'assistant'; text: string; time: string; }

@Component({
  selector: 'app-copa-assistant',
  templateUrl: './copa-assistant.component.html',
})
export class CopaAssistantComponent {
  isOpen = false;
  isLoading = false;
  input = '';
  messages: ChatMessage[] = [
    { sender: 'assistant', text: 'Olá! Sou o Copa Assistant. Pergunte sobre jogos, uniformes ou produtos da Brasildrop.', time: 'agora' }
  ];
  suggestions = ['Próximo jogo do Brasil', 'Notícias da Copa', 'Produtos do Brasil', 'Chuteiras recomendadas'];

  constructor(private copaService: CopaAssistantService) {}

  open(): void { this.isOpen = true; }
  close(): void { this.isOpen = false; }

  sendSuggestion(text: string): void { this.send(text); }

  onSubmit(): void {
    if (this.input.trim()) this.send(this.input.trim());
  }

  private send(text: string): void {
    if (this.isLoading) return;
    const time = new Date().toLocaleTimeString('pt-BR', { hour: '2-digit', minute: '2-digit' });
    this.messages.push({ sender: 'user', text, time });
    this.input = '';
    this.isLoading = true;

    this.copaService.ask(text).subscribe({
      next: data => {
        this.messages.push({ sender: 'assistant', text: data.response, time: new Date().toLocaleTimeString('pt-BR', { hour: '2-digit', minute: '2-digit' }) });
        this.isLoading = false;
      },
      error: () => {
        this.messages.push({ sender: 'assistant', text: 'Não foi possível conectar ao Copa Assistant no momento.', time: new Date().toLocaleTimeString('pt-BR', { hour: '2-digit', minute: '2-digit' }) });
        this.isLoading = false;
      }
    });
  }
}
