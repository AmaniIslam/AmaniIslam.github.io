#Amani Islam
#Lab 6.2 Encryption GUI

from tkinter import*
from Encryptor import Encryptor

class Application(Frame):
    def __init__(self, master):
        self.encryptor = Encryptor("cipher1.txt")
        super(Application, self).__init__(master)
        self.grid()
        self.create_widgets()

    def create_widgets(self):
        inp = Label(self, text = "Enter the message:")\
            .grid(row = 0, column = 1, columnspan = 2, sticky = W)
        self.inp_ent = Entry(self, width = 60)
        self.inp_ent.grid(row = 1, column = 1, columnspan = 2, sticky =W)
        enbttn = Button(self, text = "Encrypt", command = self.encrypt)\
            .grid(row = 2, column = 1, sticky = E)
        debttn = Button(self, text = "Decrypt", command = self.decrypt)\
            .grid(row = 2, column = 2, sticky = W)
        Label(self, text = "Out:")\
            .grid(row = 3, column = 1, sticky = W)
        self.out = Text(self, width = 45, height = 10, wrap = WORD)
        self.out.grid(row = 4, column = 1, columnspan = 3, sticky = W)

    def encrypt(self):
        inp = self.inp_ent.get()
        nxt = self.encryptor.encrypt_message(inp)
        self.out.delete(0.0,END)
        self.out.insert(0.0, nxt)

    def decrypt(self):
        inp = self.inp_ent.get()
        nxt = self.encryptor.decrypt_message(inp)
        self.out.delete(0.0,END)
        self.out.insert(0.0, nxt)

root = Tk()
root.title("Encrypt / Decrypt tool")
app = Application(root)
root.mainloop()