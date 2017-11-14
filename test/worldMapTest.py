#coding=utf-8
import pygal.maps.world

def map1():
    chart = pygal.maps.world.World()
    chart.title = 'countries catalog'
    chart.add('F countries', ['fr', 'fi'])
    chart.add('M countries', ['ma', 'mc', 'mg'
        , 'mk', 'ml', 'mn',
                              'mo', 'mr', 'mt',
                              'mu', 'mv', 'mw',
                              'mx', 'mz',
                              'my'])
    chart.add('U countries', ['us', 'ug', 'ua',
                              'uy', 'uz'])
    chart.render_to_file('bar_chart.svg')


#国家取值
def maps2():
    chart2 = pygal.maps.world.World()
    chart2.title = 'countries value'
    chart2.add('In 2013', {
        'af': 14,
        'bd': 1,
        'by': 3,
        'cn': 1000,
        'gm': 9,
        'in': 1,
        'ir': 314,
        'iq': 129,
        'jp': 7,
        'kp': 6,
        'pk': 1,
        'ps': 6,
        'sa': 79,
        'so': 6,
        'sd': 5,
        'tw': 6,
        'ae': 1,
        'us': 43,
        'ye': 28,
        'by': 3,
        'cn': 1000,
        'gm': 9,
        'in': 1,
        'ir': 314,
        'iq': 129,
        'jp': 7,
        'kp': 6,
        'pk': 1,
        'ps': 6,
        'sa': 79,
        'so': 6,
        'sd': 5,
        'tw': 6,
        'ae': 1,
        'us': 43,
        'ye': 28
    })
    chart2.render_to_file('In_2013.svg')

if __name__ == '__main__':
    maps2()