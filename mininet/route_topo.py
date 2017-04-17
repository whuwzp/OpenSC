from mininet.topo import Topo

class MyTopo( Topo ):

    def __init__( self ):

        # initilaize topology   
        Topo.__init__( self )

        # add hosts and switches
        h1 = self.addHost( 'h1',ip='192.168.10.1' )
        h2 = self.addHost( 'h2',ip='192.168.10.2' )
        server = self.addHost( 'server',ip='192.168.10.3' )
       
        s1 = self.addSwitch( 's1' )



        # add links
        self.addLink(h1,s1)
        self.addLink(h2,s1)
        self.addLink(server,s1)


topos = { 'mytopo': ( lambda: MyTopo() ) }
