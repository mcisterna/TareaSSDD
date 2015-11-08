Instrucciones para ejecutar:
	Desde fuera del package hacer lo siguiente:

	Primero se debe correr el ServersManager
		rmiregistry
		java cl.uchile.dcc.cc5303.ServersManager ipDelServersManager [-n numJugadores]

	donde numJugadores <= 4, si este ultimo se setea entonces empiezan todos juntos

	Luego se debe correr al menos un servidor (desde fuera del package):
		rmiregistry
		java cl.uchile.dcc.cc5303.Server ipDelServidor ipDelServersManager

	Cliente:
		java cl.uchile.dcc.cc5303.Client ipDelServersManager
