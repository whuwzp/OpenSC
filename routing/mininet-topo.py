from mininet.topo import Topo

class MyTopo( Topo ):

    def __init__( self ):

        # initilaize topology   
        Topo.__init__( self )

        # add hosts and switches
   
        s1 = self.addSwitch( 's1' )       
        s2 = self.addSwitch( 's2' )       
        s3 = self.addSwitch( 's3' )  
	
	h1 = self.addHost('h1')     
	h2 = self.addHost('h2')     





        # add links

        self.addLink(s1,s2  )
        self.addLink(s1,s3  )
        self.addLink(s2,s3  )

	self.addLink(h1,s1)
	self.addLink(h2,s2)


topos = { 'mytopo': ( lambda: MyTopo() ) } 
